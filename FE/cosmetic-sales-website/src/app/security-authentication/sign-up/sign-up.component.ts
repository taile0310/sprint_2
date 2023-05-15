import {Component, OnInit} from '@angular/core';
import {OrderDetailService} from '../../service/order-detail.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import Swal from 'sweetalert2';
import {Route, Router} from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpForm: FormGroup = new FormGroup({
    id: new FormControl(),
    name: new FormControl('', [Validators.required]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    phone: new FormControl('', [Validators.required]),
    dateOfBirth: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    gender: new FormControl('', Validators.required),

  });

  constructor(private orderDetailService: OrderDetailService,
              private router: Router) {
  }

  submitAdd() {
    if (this.signUpForm.valid) {
      this.orderDetailService.registerUser(this.signUpForm.value).subscribe(next => {
          Swal.fire({
            text: 'Đăng kí thành công. Vui lòng đăng nhập để tiếp tục mua hàng.',
            icon: 'success',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 3000
          });
          this.router.navigateByUrl('/login');
        }, error => {
          console.log(error);
          Swal.fire({
            text: 'Đăng kí thất bại.',
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


  ngOnInit(): void {
    this.view();
  }

  view(): void {
    const element = document.getElementById('sign-up');
    if (element) {
      element.scrollIntoView();
    }
  }


}
