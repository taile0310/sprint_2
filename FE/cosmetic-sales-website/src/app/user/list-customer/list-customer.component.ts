import { Component, OnInit } from '@angular/core';
import {User} from '../../model/user';
import {OrderDetailService} from '../../service/order-detail.service';

@Component({
  selector: 'app-list-customer',
  templateUrl: './list-customer.component.html',
  styleUrls: ['./list-customer.component.css']
})
export class ListCustomerComponent implements OnInit {
  users: User[];
  user: any;
  currentPage = 0;
  size = 5;
  totalPages: number;
  constructor(private orderDetailService: OrderDetailService) { }

  ngOnInit(): void {
    this.view();
    this.getAll();
  }
  getAll() {
    this.orderDetailService.getListCustomer(this.currentPage, this.size).subscribe(data => {
      this.user = data;
      this.users = this.user.content;
      this.totalPages = this.user.totalPages;
    });
  }

  previous() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getAll();
    }
  }

  next() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getAll();
    }
  }
  view(): void {
    const element = document.getElementById('customer');
    if (element) {
      element.scrollIntoView();
    }
  }
}
