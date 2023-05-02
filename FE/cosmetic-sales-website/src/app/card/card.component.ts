import {Component, OnInit} from '@angular/core';
import {User} from '../model/user';
import {Orders} from '../model/orders';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {OrdersService} from '../service/orders.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  quantity = 0;
  orders: Orders[] = [];
  total = 0;
  users: User;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private ordersService: OrdersService,
              private token: TokenStorageService,
              private shareService: ShareService) {
    this.getCart();
    this.shareService.getClickEvent().subscribe(next => {
      this.getCart();
    });
  }

  ngOnInit(): void {
  }

  getCart() {
    this.ordersService.showAllOrder(this.token.getUser()).subscribe(next => {
      console.log(next);
      this.orders = next;
      this.getValue();
    });
  }

  getValue() {
    this.total = 0;
    if (this.orders != null) {
      this.quantity = this.orders.length;
      for (let i = 0; i < this.orders.length; i++) {
        this.total += this.orders[i].product.price * this.orders[i].quantity;
      }
    }
  }

  stepUp(id: number) {
    this.ordersService.increaseQuantity(id).subscribe(next => {
      this.shareService.sendClickEvent();
      this.getCart();
      this.getValue();
    });
  }

  stepDown(id: number) {
    this.ordersService.reduceQuantity(id).subscribe(next => {
      this.shareService.sendClickEvent();
      this.getCart();
      this.getValue();
    });

  }
}
