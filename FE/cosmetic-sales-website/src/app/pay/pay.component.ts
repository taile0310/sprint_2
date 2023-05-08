import {Component, OnInit} from '@angular/core';
import {render} from 'creditcardpayments/creditCardPayments';
import {ActivatedRoute, Router} from '@angular/router';
import Swal from 'sweetalert2';
import {ShareService} from '../security-authentication/service/share.service';
import {OrderDetail} from '../model/order-detail';
import {ProductService} from '../service/product.service';
import {OrderDetailService} from '../service/order-detail.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {UserService} from '../service/user.service';
import {User} from '../model/user';


@Component({
  selector: 'app-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.css']
})
export class PayComponent implements OnInit {
  totalPrice = 0;
  orderDetail: OrderDetail[] = [];
  userId: number;
  transport = 20000;
  private username: any;
  users: User;
  id: number;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private orderDetailService: OrderDetailService,
              private userService: UserService,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }


  ngOnInit(): void {
    this.getUserId();
  }

  paid() {
    this.totalPrice = 0;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.orderDetail.length; i++) {
      this.totalPrice += this.orderDetail[i].price * this.orderDetail[i].quantity;
    }
    render({
      id: '#paypal',
      currency: 'USD',
      value: (this.totalPrice + this.transport).toString(),
      onApprove: (async details => {
        await Swal.fire({
          text: 'Thanh toán thành công.',
          icon: 'success',
          showConfirmButton: false,
          timer: 1500
        });
        await this.router.navigateByUrl('/shop');
        this.payPal(this.userId);
      })
    });
  }

  payPal(id: number) {
    this.orderDetailService.payPal(id).subscribe(() => {
    });
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.username = this.token.getUser().name;
      this.orderDetailService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;
        this.getQuantityAndTotalPrice();
      });
    } else {
      this.router.navigateByUrl('/login');
    }
  }


  getQuantityAndTotalPrice() {
    this.totalPrice = 0;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.orderDetail.length; i++) {
      this.totalPrice += this.orderDetail[i].price * this.orderDetail[i].quantity;
    }
  }


}
