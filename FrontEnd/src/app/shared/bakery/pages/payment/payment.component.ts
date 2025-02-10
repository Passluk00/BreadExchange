import {Component, OnInit} from '@angular/core';
import {FooterComponent} from "../../../components/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {Cart} from "../../../../services/models/cart";
import {BakeryNavBarResponse} from "../../../../services/models/bakery-nav-bar-response";
import {UserControllerService} from "../../../../services/services/user-controller.service";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-payment',
  imports: [
    FooterComponent,
    FormsModule,
    NavbarComponent,
    RouterLink,
    FaIconComponent,
    NgIf
  ],
  templateUrl: './payment.component.html',
  standalone: true,
  styleUrl: './payment.component.scss'
})
export class PaymentComponent implements OnInit{

  constructor(
    private route: ActivatedRoute,
    private userService: UserControllerService,
    private frontEndService: FrontEndControllerService,
  ) {
  }

  userId: number | undefined;
  data:Cart = {}
  azz:BakeryNavBarResponse = {}
  success: boolean= false;


  ngOnInit() {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
    })
    this.getData()
    this.getDataAzz()
  }


  getData(){
    this.userService.getCart().subscribe({
      next: res => {
        this.data = res;
      },
      error:() => {}
    })
  }


  getDataAzz(){
    if(this.userId != undefined) {
      this.frontEndService.getBAkeryById({
        idBac: this.userId
      }).subscribe({
        next: res => {
          this.azz= res;
        },
        error:(err) => {
          console.error("Errore nel fetch dati dati: "+err);
        }
      })
    }
  }

  sendOrder(){
    this.userService.sendOrder().subscribe({
      next:() => {
        console.error("ordine inviato")
        this.success = true
      },
      error:(err) => {
        console.error("Invio Ordine Fallito: "+err)
      }
    })
  }


}
