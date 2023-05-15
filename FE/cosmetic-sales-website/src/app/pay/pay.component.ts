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
  totalPrice2: any;
  orderDetail: OrderDetail[] = [];
  userId: number;
  transport = 20000;
  users: User;

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
      if (this.orderDetail[i].quantity <= this.orderDetail[i].quantityProduct) {
        this.totalPrice2 = ((this.totalPrice + this.transport) / 23000).toFixed(2);
        document.querySelector('#paypal').innerHTML = '';
        render({
          id: '#paypal',
          currency: 'USD',
          value: this.totalPrice2.toString(),
          onApprove: (async details => {
            // tslint:disable-next-line:prefer-for-of
            for (let j = 0; j < this.orderDetail.length; j++) {
              this.payPal(this.userId);
              this.shareService.setCount(0);
              this.orderDetailService.updateSttPalPay(this.orderDetail[j].id).subscribe(() => {
              });
            }
            Swal.fire({
              text: 'Đã quá số lượng trong kho.',
              icon: 'warning',
              iconColor: '#ecb49b',
              confirmButtonText: 'OK',
              confirmButtonColor: '#ecb49b',
              timer: 1500
            });
            this.router.navigateByUrl('/shop');
          })
        });
      } else {
        Swal.fire({
          text: 'Thanh toán thất bại.',
          icon: 'warning',
          iconColor: '#ecb49b',
          showConfirmButton: false,
          timer: 1500
        });
      }
    }
  }

  payPal(id: number) {
    this.orderDetailService.payPal(id).subscribe(() => {
    });
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.orderDetailService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;
        this.getQuantityAndTotalPrice();
      });
      this.orderDetailService.getDetailUser(this.userId).subscribe(data => {
        this.users = data;
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
