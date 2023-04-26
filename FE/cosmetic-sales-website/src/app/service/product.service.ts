import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiListProduct = 'http://localhost:8080/api/public/product/list';
  private apiDetailProduct = 'http://localhost:8080/api/public/product/detail/';

  constructor(private httpClient: HttpClient) {
  }

  getListProduct(page: number, size: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.apiListProduct + '?page=' + page + '&size=' + size);
  }
  getDetailProduct(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.apiDetailProduct + id);
  }
}
