import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {User} from '../../model/user';
import {Product} from '../../model/product';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  users: User[];
  user: any;
  currentPage = 0;
  size = 5;
  totalPages: number;


  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.userService.getListUser(this.currentPage, this.size).subscribe(data => {
      this.user = data;
      this.users = this.user.content;
      this.totalPages = this.user.totalPages;
    });
  }

  previous() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getAll();
    }
  }

  next() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getAll();
    }
  }
}
