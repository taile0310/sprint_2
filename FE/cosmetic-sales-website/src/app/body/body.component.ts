import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    this.view();
  }

  view(): void {
    const element = document.getElementById('header');
    if (element) {
      element.scrollIntoView();
    }
  }

}
