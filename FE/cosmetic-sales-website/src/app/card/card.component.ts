import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderDetailService} from '../service/order-detail.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';
import {OrderDetail} from '../model/order-detail';
import Swal from 'sweetalert2';

// @ts-ignore
@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  hasItems: boolean;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private orderDetailService: OrderDetailService,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }

  orderDetail: OrderDetail[] = [];
  userId: number;
  totalPrice = 0;
  mess = '';

  ngOnInit(): void {
    this.view();
    this.getUserId();
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.orderDetailService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;
        this.getQuantityAndTotalPrice();
      });
    } else {
      this.router.navigateByUrl('/login');
    }
  }


  inc(order: OrderDetail) {
    order.quantity++;
    this.getCart(order.id, order.quantity);
  }

  desc(order: OrderDetail) {
    if (order.quantity > 1) {
      order.quantity--;
      this.getCart(order.id, order.quantity);
    }
  }

  getCart(orderDetailId: number, quantity: number) {
    this.orderDetailService.changeQuantity(orderDetailId, quantity).subscribe(() => {
      this.orderDetailService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;
        this.getQuantityAndTotalPrice();
      });
    });
  }

 async getQuantityAndTotalPrice() {
    this.totalPrice = 0;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.orderDetail.length; i++) {
      this.totalPrice += this.orderDetail[i].price * this.orderDetail[i].quantity;
    }
    await this.updateHasItems();
  }
  // xử lý bất đồng bộ
  async updateHasItems() {
    this.hasItems = this.orderDetail.length > 0;
  }
  deleteOrderDetail(productId: number, cartId: number) {
    this.orderDetailService.deleteOrderDetailById(productId, cartId).subscribe(data => {
      Swal.fire({
        text: 'Bỏ chọn sản phẩm thành công.',
        icon: 'warning',
        iconColor: '#ecb49b',
        confirmButtonText: 'OK',
        confirmButtonColor: '#ecb49b',
        timer: 1500
      });
      this.orderDetail = this.orderDetail.filter(order => order.id !== cartId);
      setTimeout(() => {
        this.hasItems = this.orderDetail.length > 0;
      }, 500);
      this.getQuantityAndTotalPrice();
    });
    this.getUserId();
  }


  view(): void {
    const element = document.getElementById('cart');
    if (element) {
      element.scrollIntoView();
    }
  }
}
