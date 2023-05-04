import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
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

  addToCart(userId: number, productId: number, quantity: number) {
    return this.httpClient.get<any>(
      this.api + '/addCart?userId=' + userId + '&productId=' + productId + '&quantity=' + quantity);
  }
}
