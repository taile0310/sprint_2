import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {UserService} from '../service/user.service';
import {ProductService} from '../service/product.service';
import {OrderDetailService} from '../service/order-detail.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {
  products: Product[] = [];
  son: Product[] = [];
  kemNen: Product[] = [];
  tayTrang: Product[] = [];
  xitKhoang: Product[] = [];
  bangMat: Product[] = [];
  phanMa: Product[] = [];
  ckd: Product[] = [];
  phanPhu: Product[] = [];
  highlights: Product[] = [];
  trietSac: Product[] = [];


  constructor(private userService: UserService,
              private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private activatedRoute: ActivatedRoute,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private token: TokenStorageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getAll();
    this.view();
  }

  getAll() {
    this.productService.getListSON().subscribe(data => {
      console.log(this.son);
      this.son = data;
    });
    this.productService.getListKEMNEN().subscribe(data => {
      this.kemNen = data;
    });
    this.productService.getListTAYTRANG().subscribe(data => {
      this.tayTrang = data;
    });
    this.productService.getListXITKHOANG().subscribe(data => {
      this.xitKhoang = data;
    });
    this.productService.getListBANGMAT().subscribe(data => {
      this.bangMat = data;
    });
    this.productService.getListPHANMA().subscribe(data => {
      this.phanMa = data;
    });
    this.productService.getListCHEKHUYETDIEM().subscribe(data => {
      this.ckd = data;
    });
    this.productService.getListPHANPHU().subscribe(data => {
      this.phanPhu = data;
    });
    this.productService.getListHIGHLIGHT().subscribe(data => {
      this.highlights = data;
    });
    this.productService.getListTRIETSAC().subscribe(data => {
      this.trietSac = data;
    });
  }

  view(): void {
    const element = document.getElementById('body');
    if (element) {
      element.scrollIntoView();
    }
  }
}

