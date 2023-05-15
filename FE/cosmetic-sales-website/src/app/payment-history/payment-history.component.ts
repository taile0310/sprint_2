import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {OrderDetailService} from '../service/order-detail.service';
import {OrderDetail} from '../model/order-detail';
import {User} from '../model/user';
import {ShareService} from '../security-authentication/service/share.service';

@Component({
  selector: 'app-payment-history',
  templateUrl: './payment-history.component.html',
  styleUrls: ['./payment-history.component.css']
})
export class PaymentHistoryComponent implements OnInit {
  userId: number;
  orderDetail: OrderDetail[] = [];
  orderDetails: any;
  currentPage = 0;
  size = 5;
  totalPages: number;
  username = '';

  constructor(private router: Router,
              private orderDetailService: OrderDetailService,
              private activatedRoute: ActivatedRoute,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getUserId();
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.username = this.token.getUser().name;
      this.orderDetailService.showPaymentHistory(this.userId, this.currentPage, this.size).subscribe(data => {
        this.orderDetails = data;
        this.orderDetail = this.orderDetails.content;
        this.totalPages =  this.orderDetails.totalPages;
      });
    } else {
      this.router.navigateByUrl('/login');
    }
  }
  previous() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getUserId();
    }
  }

  next() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getUserId();
    }
  }
  view(): void {
    const element = document.getElementById('history');
    if (element) {
      element.scrollIntoView();
    }
  }
}
