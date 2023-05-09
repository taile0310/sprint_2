import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';
import {Category} from '../model/category';
import {OrderDetailService} from '../service/order-detail.service';
import {ShareService} from '../security-authentication/service/share.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

  products: Product[] = [];
  category: Category[] = [];
  product: any;
  currentPage = 0;
  size = 6;
  totalPages = 0;
  categoryId = 0;
  productName = '';
  userId: number;
  searchError: boolean;

  constructor(private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private shareService: ShareService,
              private token: TokenStorageService,
              private router: Router) {
    this.productService.getListCategory().subscribe(data => {
      this.category = data;
    });
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
    } else {
      this.router.navigateByUrl('/shop');
    }
  }


  ngOnInit(): void {
    this.view();
    this.getAll();
    this.getUserId();

  }


  getAll() {
    this.productService.getListProduct(this.currentPage, this.size, this.productName, this.categoryId).subscribe(data => {
      console.log(data);
      this.product = data;
      if (this.product !== null) {
        this.products = this.product.content;
        this.totalPages = this.product.totalPages;
        this.searchError = false;
      } else {
        this.products = [];
        this.totalPages = 0;
        this.searchError = true;
      }
    });
  }

  search() {
    this.currentPage = 0;
    this.productService.getListProduct(this.currentPage, this.size, this.productName, this.categoryId).subscribe(data => {
      console.log(data);
      this.product = data;
      if (this.product !== null) {
        this.products = this.product.content;
        this.totalPages = this.product.totalPages;
        this.searchError = false;
      } else {
        this.products = [];
        this.totalPages = 0;
        this.searchError = true;
      }
    });
  }
  clearInputs() {
    this.productName = '';
    this.categoryId = 0;
    this.getAll();
  }


  previous() {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
    this.getAll();

  }

  next() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
    }
    this.getAll();

  }

  addToCart(productId: number, quantity: number) {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.orderDetailService.addToCart(this.userId, productId, quantity).subscribe(() => {
        Swal.fire({
          text: 'Sản phẩm đã được thêm vào giỏ hàng.',
          icon: 'success',
          iconColor: '#ecb49b',
          confirmButtonText: 'OK',
          confirmButtonColor: '#ecb49b',
          timer: 1500
        });
      });
    } else {
      this.router.navigateByUrl('/login');
    }
    // this.orderDetailService.addToCart(this.userId, productId, quantity).subscribe(() => {
    // });
  }

  view(): void {
    const element = document.getElementById('shop');
    if (element) {
      element.scrollIntoView();
    }
  }
}
