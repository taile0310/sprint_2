import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  products: Product[] = [];
  product: any;
  currentPage = 0;
  size = 5;
  totalPages: number;
  page = 0;

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.productService.getListProduct(this.currentPage, this.size).subscribe(data => {
      console.log(data);

      this.product = data;
      this.products = this.product.content;
      this.totalPages = this.product.totalPages;
    });
  }

  previous() {
    if (this.page > 0) {
      this.page--;
      this.getAll();
    }
  }

  next() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.getAll();
    }
  }


}
