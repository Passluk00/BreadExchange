import { Component } from '@angular/core';
import {NgIf} from "@angular/common";
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {ListRequestsComponent} from "./components/list-requests/list-requests.component";
import {ListUsersComponent} from "./components/list-users/list-users.component";
import {ListUserClickComponent} from "./components/list-user-click/list-user-click.component";
import {ListBakeryComponent} from "./components/list-bakery/list-bakery.component";

@Component({
  selector: 'app-admin',
  imports: [
    NgIf,
    FaIconComponent,
    ListRequestsComponent,
    ListUsersComponent,
    ListUserClickComponent,
    ListBakeryComponent
  ],
  templateUrl: './admin.component.html',
  standalone: true,
  styleUrl: './admin.component.scss'
})
export class AdminComponent {

  visibleElement: string | null = 'ban/unban';      // sostituire con dashboard

  showElement(element:string, event:Event){
    event.preventDefault()
    this.visibleElement = element;
  }



  protected readonly faMagnifyingGlass = faMagnifyingGlass;
}
