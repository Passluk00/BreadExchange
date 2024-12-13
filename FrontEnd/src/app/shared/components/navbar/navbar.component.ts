import {Component, HostListener} from '@angular/core';
import {RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faArrowRightFromBracket,
  faCartShopping,
  faChartLine,
  faCircleQuestion, faMagnifyingGlass,
  faSliders, faUser
} from "@fortawesome/free-solid-svg-icons";
import {NgIf} from "@angular/common";



@Component({
  selector: 'app-navbar',
  imports: [
    FaIconComponent,
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  protected readonly faArrowRightFromBracket = faArrowRightFromBracket;
  protected readonly faCircleQuestion = faCircleQuestion;
  protected readonly faSliders = faSliders;
  protected readonly faChartLine = faChartLine;
  protected readonly faCartShopping = faCartShopping;
  protected readonly faUser = faUser;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;



  userData:any = {
    username:"Passluk",
    email:"test@mail.com",
    address:{
      name:"casa",
      country:"Italy",
      state:"Lazio",
      provincia:"Viterbo",
      city:"Pescia Romana",
      postalCode:"01014",
      street:"Strada Cerquabella",
      number:"13"
    },
    url_Profile:"./public/testImg/profile.jpeg",
  }





}
