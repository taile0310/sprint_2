import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {
  constructor(private token: TokenStorageService,
              private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.token.getToken()) {
      // tslint:disable-next-line:max-line-length
      if ((this.token.getRole() === 'ROLE_ADMIN' || this.token.getRole() === 'ROLE_EMPLOYEE' || this.token.getRole() === 'ROLE_CUSTOMER') && this.token.isLogger()) {
        return true;
      } else {
        this.router.navigateByUrl('/error');
        return false;
      }
    } else {
      this.router.navigateByUrl('/error');
      return false;
    }
  }

}
