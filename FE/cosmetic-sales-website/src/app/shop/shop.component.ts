import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';
import {Category} from '../model/category';
import {OrdersService} from '../service/orders.service';
import {ShareService} from '../security-authentication/service/share.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {Orders} from '../model/orders';
import {Router} from '@angular/router';

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

  constructor(private productService: ProductService,
              private ordersService: OrdersService,
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
      this.router.navigateByUrl('/login');
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
      this.products = this.product.content;
      this.totalPages = this.product.totalPages;
    });
  }

  search() {
    this.currentPage = 0;
    this.productService.getListProduct(this.currentPage, this.size, this.productName, this.categoryId).subscribe(data => {
      console.log(data);
      this.product = data;
      this.products = this.product.content;
      this.totalPages = this.product.totalPages;
    });
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
  view(): void {
    const element = document.getElementById('shop');
    if (element) {
      element.scrollIntoView();
    }
  }


    addToCart(productId: number, quantity: number) {
      this.ordersService.addToCart(this.userId, productId, quantity).subscribe(() => {
      });
    }

}
