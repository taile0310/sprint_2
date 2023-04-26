import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute} from '@angular/router';
import {Product} from '../model/product';

@Component({
  selector: 'app-detail-shop',
  templateUrl: './detail-shop.component.html',
  styleUrls: ['./detail-shop.component.css']
})
export class DetailShopComponent implements OnInit {
  product: Product;
  id: number;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
  }

  getDetailProduct() {
    this.activatedRoute.paramMap.subscribe(data => {
      console.log(data);
      this.id = +data.get('id');
    });
    this.productService.getDetailProduct(this.id).subscribe(data => {
      this.product = data;
    });
  }
}
