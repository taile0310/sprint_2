import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../model/product';
import {Category} from '../model/category';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {OrderDetailService} from '../service/order-detail.service';
import {OrderDetail} from '../model/order-detail';

@Component({
  selector: 'app-detail-shop',
  templateUrl: './detail-shop.component.html',
  styleUrls: ['./detail-shop.component.css']
})
export class DetailShopComponent implements OnInit {
  id: number;
  product: Product;
  userId: number;
  totalPrice = 0;

  constructor(private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private token: TokenStorageService) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
    this.view();
    this.getUserId();
  }


  getDetailProduct() {
    this.activatedRoute.paramMap.subscribe(data => {
      this.id = +data.get('id');
    });
    this.productService.getDetailProduct(this.id).subscribe(data => {
      this.product = data;
    });
  }

  view(): void {
    const element = document.getElementById('detail-shop');
    if (element) {
      element.scrollIntoView();
    }
  }

  addToCart(productId: number, quantity: number) {
    this.orderDetailService.addToCart2(this.userId, productId, quantity).subscribe(() => {
    });
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
    } else {
      this.router.navigateByUrl('/login');
    }
  }


  inc() {
    this.product.quantity++;
  }

  desc() {
    if (this.product.quantity > 1) {
      this.product.quantity--;
    }
  }

}
