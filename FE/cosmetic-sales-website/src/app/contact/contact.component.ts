import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    this.view();
  }

  view(): void {
    const element = document.getElementById('contact');
    if (element) {
      element.scrollIntoView();
    }
  }
}
