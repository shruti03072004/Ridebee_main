import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { NgZorroModule } from 'src/app/NgZorroModule';
import { BookCarComponent } from './components/book-car/book-car.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GetBookingsComponent } from './components/get-bookings/get-bookings.component';
import { SearchCarComponent } from './components/search-car/search-car.component';
import { CustomerReviewsComponent } from './components/customer-reviews/customer-reviews.component';

@NgModule({
  declarations: [
    CustomerDashboardComponent,
    BookCarComponent,
    GetBookingsComponent,
    SearchCarComponent,
    CustomerReviewsComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    NgZorroModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CustomerModule { }