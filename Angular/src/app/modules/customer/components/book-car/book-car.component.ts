import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

declare var Razorpay: any;

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss']
})
export class BookCarComponent {

  totalAmount: number = 0;
  totalDays: number = 0;

  car: any;
  carId: number = this.activated.snapshot.params["id"];
  bookACarForm!: FormGroup;
  isSpinning = false;
  bookingStatus: 'success' | 'failed' | null = null;

  constructor(
    private customerService: CustomerService,
    private message: NzMessageService,
    private activated: ActivatedRoute,
    private fb: FormBuilder
  ) {

    this.bookACarForm = this.fb.group({
      fromDate: [null, Validators.required],
      toDate: [null, Validators.required]
    });

    // ✅ AUTO CALCULATE
    this.bookACarForm.valueChanges.subscribe(() => {
      this.calculateAmount();
    });

    this.getCarById();
  }

  // ✅ CALCULATE DAYS + PRICE
  calculateAmount() {

    const from = this.bookACarForm.get('fromDate')?.value;
    const to = this.bookACarForm.get('toDate')?.value;

    if (from && to && this.car) {

      const fromDate = new Date(from);
      const toDate = new Date(to);

      const diffTime = toDate.getTime() - fromDate.getTime();
      const days = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (days > 0) {
        this.totalDays = days;
        this.totalAmount = days * this.car.price;
      } else {
        this.totalDays = 0;
        this.totalAmount = 0;
      }
    }
  }

  // ✅ GET CAR
  getCarById() {
    this.customerService.getCarById(this.carId).subscribe((res) => {
      res.processedImg = 'data:image/jpeg;base64,' + res.returnedImage;
      this.car = res;

      this.calculateAmount(); // 🔥 important
    });
  }

  // ✅ PAYMENT (FIXED HERE)
  payNow() {
    if (!this.bookACarForm.valid) {
      this.message.error("Please select dates");
      return;
    }

    if (this.totalAmount <= 0) {
      this.message.error("Invalid amount");
      return;
    }

    this.isSpinning = true;
    this.bookingStatus = null;

    // 🔥 MAIN FIX: USE totalAmount
    this.customerService.createOrder(this.totalAmount).subscribe((order: any) => {

      const options: any = {
        key: "rzp_test_SDzYuoGkZunvpH",
        amount: order.amount,
        currency: "INR",
        name: "RideBee",
        description: `Booking for ${this.totalDays} days`,
        order_id: order.id,

        handler: (response: any) => {
          console.log("✅ Payment Success", response);
          this.bookCar(this.bookACarForm.value);
        },

        modal: {
          ondismiss: () => {
            this.isSpinning = false;
            this.message.warning("Payment cancelled");
          }
        },

        prefill: {
          name: "User",
          email: "test@gmail.com"
        },

        theme: {
          color: "#E8720C"
        }
      };

      const rzp = new Razorpay(options);

      rzp.on('payment.failed', () => {
        this.isSpinning = false;
        this.bookingStatus = 'failed';
        this.message.error("Payment Failed ❌");
      });

      rzp.open();
      this.isSpinning = false;

    }, error => {
      this.isSpinning = false;
      this.message.error("Payment failed to start");
    });
  }

  // ✅ BOOK CAR
  bookCar(formData: any) {

    this.isSpinning = true;

    const obj = {
      fromDate: formData.fromDate,
      toDate: formData.toDate,
      userId: StorageService.getUserId()
    };

    this.customerService.bookACar(this.carId, obj).subscribe(() => {
      this.isSpinning = false;
      this.bookingStatus = 'success';
      this.message.success("Car booked successfully");

    }, () => {
      this.isSpinning = false;
      this.bookingStatus = 'failed';
      this.message.error("Booking failed");
    });
  }
}