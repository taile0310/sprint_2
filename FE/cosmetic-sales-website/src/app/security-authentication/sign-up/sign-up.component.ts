import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  constructor() { }

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
