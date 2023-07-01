import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import Swal from 'sweetalert2';
import {Category} from '../model/category';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  category: Category[] = [];
  categoryId: number;
  productGroup: FormGroup = new FormGroup({
    productName: new FormControl('', [Validators.required]),
    productDescription: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required]),
    cost: new FormControl('', [Validators.required]),
    image: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
  });
   selectedImage: any;

  constructor(private productService: ProductService,
              private router: Router,
              private token: TokenStorageService,
              private activatedRoute: ActivatedRoute,
  ) {
    this.productService.getListCategory().subscribe(data => {
      this.category = data;
    });
  }

  ngOnInit(): void {
  }

  addNewProduct() {
    if (this.productGroup.valid) {
      this.productService.addNewProduct(this.productGroup.value).subscribe(next => {
          Swal.fire({
            text: 'Thêm mới sản phẩm thành công.',
            icon: 'success',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 3000
          });
        }, error => {
          console.log(error);
          Swal.fire({
            text: 'Thêm mới thất bại.',
            icon: 'warning',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 3000
          });
        }
      );
    }
  }

  onImageChange(event: any) {
    const file = event.target.files[0]; // Lấy tệp tin hình ảnh đầu tiên từ sự kiện change
    this.selectedImage = URL.createObjectURL(file);
    // Xử lý file hình ảnh ở đây
    console.log(file);
  }

}
