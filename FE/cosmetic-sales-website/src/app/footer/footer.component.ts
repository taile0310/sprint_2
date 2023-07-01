import {Component, HostListener, OnInit} from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  showBackToTop = false;

  constructor() {
  }

  ngOnInit() {
    this.scrollToTop();
  }


  @HostListener('window:scroll')
  onWindowScroll() {
    this.showBackToTop = (window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop) > 500;
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
