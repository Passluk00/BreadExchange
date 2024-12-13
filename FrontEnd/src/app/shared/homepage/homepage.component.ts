import { Component } from '@angular/core';
import {FooterComponent} from "../components/footer/footer.component";
import {NavbarComponent} from "../components/navbar/navbar.component";

@Component({
  selector: 'app-homepage',
  imports: [FooterComponent, NavbarComponent],
  templateUrl: './homepage.component.html',
  standalone: true,
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {

}
