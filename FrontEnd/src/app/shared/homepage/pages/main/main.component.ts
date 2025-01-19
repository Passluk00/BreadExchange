import { Component } from '@angular/core';
import {CaroselloComponent} from "../../../components/carosello/carosello.component";
import {MarketingComponent} from "../../../components/marketing/marketing.component";

@Component({
  selector: 'app-main',
  imports: [
    CaroselloComponent,
    MarketingComponent
  ],
  templateUrl: './main.component.html',
  standalone: true,
  styleUrl: './main.component.scss'
})
export class MainComponent {

}
