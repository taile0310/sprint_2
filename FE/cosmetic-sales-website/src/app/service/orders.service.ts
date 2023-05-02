import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private httpClient: HttpClient) {
  }

  showAllOrder(id: number): Observable<any> {
    return this.httpClient.get<any>('http://localhost:8080/api/public/' + id);
  }

  createOrders(userId: number, productId: number, quantity: number, totalAmount: number): Observable<any> {
    // tslint:disable-next-line:prefer-const
    let dto = { userId, productId, quantity, totalAmount };
    return this.httpClient.post('http://localhost:8080/api/public/create', dto);
  }

  increaseQuantity(id): Observable<any> {
    // tslint:disable-next-line:prefer-const
    let dto = {id};
    return this.httpClient.post('http://localhost:8080/api/public/increase', dto);
  }

  reduceQuantity(id): Observable<any> {
    // tslint:disable-next-line:prefer-const
    let dto = {id};
    return this.httpClient.post('http://localhost:8080/api/public/reduce', dto);
  }
}
