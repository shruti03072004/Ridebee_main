import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-customer-reviews',
  templateUrl: './customer-reviews.component.html',
  styleUrls: ['./customer-reviews.component.css']
})
export class CustomerReviewsComponent implements OnInit, OnDestroy {

  current = 0;
  total = 5;
  autoplay: any;

  reviews = [
    { initials: 'AR', name: 'ARJUN RAWAL',    meta: 'Mumbai · April 2026',    quote: 'Absolutely loved the experience! The Tesla Cybertruck was pristine and the pickup was smooth. Best premium car rental I have had in years.', badge: 'Tesla Cybertruck · Electric',  rating: 5 },
    { initials: 'PS', name: 'PRIYA SHARMA',   meta: 'Pune · March 2026',      quote: 'The Bugatti was a dream to drive on the expressway. The team was professional, the handover took minutes, and the car was immaculate.', badge: 'Bugatti Chiron · Supercar',    rating: 5 },
    { initials: 'RK', name: 'ROHIT KULKARNI', meta: 'Nashik · April 2026',    quote: 'Rented for my wedding and received countless compliments. The car arrived on time, fully detailed. The team made the day truly special.', badge: 'Mercedes S-Class · Luxury',   rating: 5 },
    { initials: 'NK', name: 'NEHA KAPOOR',    meta: 'Delhi · February 2026',  quote: 'Seamless booking process, transparent pricing, and the BMW M5 handled the highway like a beast. Customer support was very responsive.', badge: 'BMW M5 · Sport',               rating: 4 },
    { initials: 'SM', name: 'SURESH MEHTA',   meta: 'Bangalore · March 2026', quote: 'Outstanding service from start to finish. The Lamborghini was exactly as shown — no hidden fees, no surprises. Absolutely premium.', badge: 'Lamborghini Urus · SUV',        rating: 5 },
  ];

  getStars(rating: number): boolean[] {
    return [1, 2, 3, 4, 5].map(s => s <= rating);
  }

  goTo(n: number) {
    this.current = (n + this.total) % this.total;
  }

  prev() { this.goTo(this.current - 1); }
  next() { this.goTo(this.current + 1); }

  ngOnInit() {
    this.autoplay = setInterval(() => this.next(), 4000);
  }

  ngOnDestroy() {
    clearInterval(this.autoplay);
  }
}