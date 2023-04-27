import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';
import {Category} from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiListCategory = 'http://localhost:8080/api/public/product/list-category';
  private apiListProduct = 'http://localhost:8080/api/public/product/list';
  private apiDetailProduct = 'http://localhost:8080/api/public/product/detail/';

  constructor(private httpClient: HttpClient) {
  }

  getListProduct(page: number, size: number, productName: string, categoryId: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>
    (this.apiListProduct + '?page=' + page + '&size=' + size + '&productName=' + productName + '&categoryId=' + categoryId);
  }
  getDetailProduct(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.apiDetailProduct + id);
  }

  getListCategory(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.apiListCategory);
  }
}
