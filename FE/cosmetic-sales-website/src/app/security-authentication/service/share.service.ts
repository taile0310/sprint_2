import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
import {OrdersService} from '../../service/orders.service';
import {Router} from '@angular/router';
import {Orders} from '../../model/orders';

@Injectable({
  providedIn: 'root'
})
export class ShareService {
  totalProduct = 0;
  userId: number;
  orders: Orders[];

  constructor(private token: TokenStorageService,
              private ordersService: OrdersService,
              private router: Router) {
  }

  private subject = new Subject<any>();

  sendClickEvent() {
    this.subject.next();
  }

  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }

  // getTotalProduct() {
  //   return new Observable<number>((observer) => {
  //     if (this.token.getToken()) {
  //       this.userId = this.token.getUser().userId;
  //       this.ordersService.showAllOrder(this.userId).subscribe(data => {
  //         this.orders = data;
  //         this.getQuantity();
  //         observer.next(this.totalProduct);
  //         observer.complete();
  //       });
  //     } else {
  //       this.router.navigateByUrl('/login');
  //       observer.next(0);
  //       observer.complete();
  //     }
  //   });
  // }

  // private getQuantity() {
  //   this.totalProduct = 0;
  //   if (this.orders) {
  //     // tslint:disable-next-line:prefer-for-of
  //     for (let i = 0; i < this.orders.length; i++) {
  //       // tslint:disable-next-line:radix
  //       this.totalProduct += parseInt(String(this.orders[i].quantity));
  //     }
  //   }
  // }
}
