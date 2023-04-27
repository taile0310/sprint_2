import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';
import {Category} from '../model/category';

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

  constructor(private productService: ProductService) {
    this.productService.getListCategory().subscribe(data => {
      this.category = data;
    });
  }

  ngOnInit(): void {
    this.view();
    this.getAll();
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

}
