import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OrderDetail} from '../model/order-detail';
import {Product} from '../model/product';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class OrderDetailService {
  private api = 'http://localhost:8080/api/public';

  constructor(private httpClient: HttpClient) {
  }

  showAllOrder(id: number): Observable<any> {
    return this.httpClient.get<any>('http://localhost:8080/api/public/cart/' + id);
  }

  changeQuantity(orderDetailId: number, quantity: number) {
    return this.httpClient.get<any>(
      this.api + '/changeQuantity?orderDetailId=' + orderDetailId + '&quantity=' + quantity);
  }

// add in shop
  addToCart(userId: number, productId: number, quantity: number) {
    return this.httpClient.get<any>(
      this.api + '/addCart?userId=' + userId + '&productId=' + productId + '&quantity= ' + quantity);
  }

// add in detail-shop
  addToCart2(userId: number, productId: number, quantity: number) {
    return this.httpClient.get<any>(
      this.api + '/addCart2?userId=' + userId + '&productId=' + productId + '&quantity= ' + quantity);
  }

  deleteOrderDetailById(productId: number, cartId: number) {
    return this.httpClient.delete(this.api + '/order-detail/' + productId + '/' + cartId);
  }

  findOrderDetailByUserId(id: number): Observable<any> {
    return this.httpClient.get<any>(this.api + '/detail/' + id);
  }

  payPal(id: number) {
    return this.httpClient.get(this.api + '/paypal?id= ' + id);
  }

  updateSttPalPay(odId: number): Observable<any> {
    return this.httpClient.get<any>(this.api + '/update?odId=' + odId);
  }

  showPaymentHistory(id: number, page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(this.api + '/payment-history/' + id + '?page=' + page + '&size=' + size);
  }

  getDetailUser(id: number): Observable<User> {
    return this.httpClient.get<User>(this.api + '/detail?id=' + id);
  }

  getListCustomer(page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(this.api + '/list-customer' + '?page=' + page + '&size=' + size);
  }
}
