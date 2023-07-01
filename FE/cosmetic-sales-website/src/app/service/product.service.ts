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
  private apiListProductHomePage = 'http://localhost:8080/api/public/product/page-list';
  private apiDetailProduct = 'http://localhost:8080/api/public/product/detail/';
  private apiDetailProductForUpdate = 'http://localhost:8080/api/public/product/update-detail/';
  private apiDelete = 'http://localhost:8080/api/public/product/';
  private apiUpdate = 'http://localhost:8080/api/public/product/update/';
  private apiAddNewProduct = 'http://localhost:8080/api/public/product/add';

  constructor(private httpClient: HttpClient) {
  }

  getListProduct(page: number, size: number, productName: string, categoryId: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>
    (this.apiListProduct + '?page=' + page + '&size=' + size + '&productName=' + productName + '&categoryId=' + categoryId);
  }

  getListProductHomePage(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.apiListProductHomePage);
  }

  getDetailProduct(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.apiDetailProduct + id);
  }

  getDetailProductForUpdate(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.apiDetailProductForUpdate + id);
  }

  getListCategory(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.apiListCategory);
  }

  deleteById(id: number) {
    return this.httpClient.delete(this.apiDelete + id);
  }

  updateProduct(product: any) {
    return this.httpClient.patch(this.apiUpdate + product.id, product);
  }

  addNewProduct(product: any): Observable<any> {
    return this.httpClient.post<any>(this.apiAddNewProduct, product);
  }

  getListSON(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/SON');
  }

  getListKEMNEN(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/KEM-NEN');
  }

  getListTAYTRANG(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/TAY-TRANG');
  }

  getListXITKHOANG(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/XIT-KHOANG');
  }

  getListBANGMAT(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/BANG-MAT');
  }

  getListPHANMA(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/PHAN-MA');
  }

  getListCHEKHUYETDIEM(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/CKD');
  }

  getListPHANPHU(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/PHAN-PHU');
  }

  getListHIGHLIGHT(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/HIGHLIGHT');
  }

  getListTRIETSAC(): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/api/public/product/TRIET-SAC');
  }

  filterPrice(page: number, size: number, minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      // tslint:disable-next-line:max-line-length
      'http://localhost:8080/api/public/product/filter-prices?page=' + page + '&size=' + size +
      '&minPrice=' + minPrice + '&maxPrice=' + maxPrice);
  }
}
