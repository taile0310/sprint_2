import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute} from '@angular/router';
import {Product} from '../model/product';
import {Category} from '../model/category';

@Component({
  selector: 'app-detail-shop',
  templateUrl: './detail-shop.component.html',
  styleUrls: ['./detail-shop.component.css']
})
export class DetailShopComponent implements OnInit {
  id: number;
  product: Product;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
    this.view()
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

  view(): void {
    const element = document.getElementById('detail-shop');
    if (element) {
      element.scrollIntoView();
    }
  }
}
