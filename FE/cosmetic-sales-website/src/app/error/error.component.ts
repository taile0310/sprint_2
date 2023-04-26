import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  isLoggedIn = false;
  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.view();
    this.isLoggedIn = this.tokenStorageService.isLogger();
  }
  view(): void {
    const element = document.getElementById('error-page');
    if (element) {
      element.scrollIntoView();
    }
  }


}
