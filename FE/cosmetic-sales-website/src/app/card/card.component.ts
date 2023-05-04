import {Component, OnInit} from '@angular/core';
import {Orders} from '../model/orders';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {OrdersService} from '../service/orders.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';
import {OrderDetail} from '../model/order-detail';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  orderDetail: OrderDetail[] = [];
  userId: number;
  totalPrice: number;
  totalProduct: number;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private ordersService: OrdersService,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getUserId();
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.ordersService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;
        console.log(this.orderDetail[0].quantity);
        // this.getQuantityAndTotalPrice();
      });
    } else {
      this.router.navigateByUrl('/login');
    }
  }


  // changQuantity(event, order: Orders) {
  //   order.quantity = event.target.value;
  //   this.getCart(order.id, order.quantity);
  // }
  //
  // inc(order: Orders) {
  //   order.quantity++;
  //   this.getCart(order.id, order.quantity);
  // }
  //
  // desc(order: Orders) {
  //   if (order.quantity > 1) {
  //     order.quantity--;
  //     this.getCart(order.id, order.quantity);
  //   }
  // }

  getCart(orderDetailId: number, quantity: number) {
    this.ordersService.changeQuantity(orderDetailId, quantity).subscribe(() => {
      this.ordersService.showAllOrder(this.userId).subscribe(data => {
        this.orderDetail = data;

        // this.getQuantityAndTotalPrice();
      });
    });
  }

  // getQuantityAndTotalPrice() {
  //   this.totalProduct = 0;
  //   this.totalPrice = 0;
  //   if (this.orderDetail) {
  //     // tslint:disable-next-line:prefer-for-of
  //     for (let i = 0; i < this.orderDetail?.length; i++) {
  //       // @ts-ignore
  //       // tslint:disable-next-line:radix
  //       this.totalProduct += parseInt(this.orders[i].quantity);
  //       this.totalPrice += (this.orderDetail[i].price * this.orderDetail[i].quantity);
  //     }
  //     this.shareService.getTotalProduct().subscribe(totalProduct => {
  //       this.totalProduct = totalProduct;
  //       this.shareService.sendClickEvent();
  //     });
  //     console.log(this.orderDetail);
  //   }
  // }


}
