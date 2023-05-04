import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderDetailService {
  private api = 'http://localhost:8080/api/public/cart-detail';


  constructor(private httpClient: HttpClient) {
  }

  showAllOrderDetail(): Observable<any> {
    return this.httpClient.get<any>(this.api);
  }

}
