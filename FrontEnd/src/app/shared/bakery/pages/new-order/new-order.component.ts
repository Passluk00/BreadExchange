import {Component, OnInit} from '@angular/core';
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {UserControllerService} from "../../../../services/services/user-controller.service";
import {ListCatForCart} from "../../../../services/models/list-cat-for-cart";
import {FormsModule} from "@angular/forms";
import {ItemForCart} from "../../../../services/models/item-for-cart";

@Component({
  selector: 'app-new-order',
  imports: [
    NgForOf,
    NgIf,
    NavbarComponent,
    FooterComponent,
    FaIconComponent,
    FormsModule,
    RouterLink
  ],
  templateUrl: './new-order.component.html',
  standalone: true,
  styleUrl: './new-order.component.scss'
})
export class NewOrderComponent implements OnInit{



  constructor(
    private frontEndService: FrontEndControllerService,
    private userService: UserControllerService,
    private route: ActivatedRoute,
  ) {
  }

  userId: number | undefined;
  data:ListCatForCart = {}
  isLogged: boolean = false;



  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
    })
    this.getData()
  }


  getData(){
    if(this.userId){
      this.userService.getAllForCart({
        idBac:this.userId
      }).subscribe({
        next: (res) => {
          this.data = res
        },
        error: (err) => {
          console.error("Errore nel Fetch Dati: " + err)
        }
      })
    }
  }

  addToCart(item:ItemForCart){

    if(
      this.userId != undefined &&
      item != undefined &&
      item.id != null &&
      item.quantity != undefined
    ){

      this.userService.addToCart({
        idBac: this.userId,
        idItem: item.id,
        qnt: item.quantity
      }).subscribe({
        next:(res) => {
          console.error("Aggiunta in Cart avvenuta: "+res)
        },
        error: (err) => {
          console.error("Errore Aggiunta In Cart: "+ err)
        }
      })

    }

  }



  removeOneToQuantity(item: ItemForCart) {
    if(item.quantity != undefined) {

      if(item.quantity > 0 ){
        item.quantity--;
        console.error("valore: "+ item.quantity)
      }
    }
  }

  addOneToQuantity(item: ItemForCart){
    if(item != undefined && item.quantity != undefined) {

      if(item.quantity < 99 ){
        item.quantity++;
        console.error("valore: "+ item.quantity)
      }
    }
  }




}
