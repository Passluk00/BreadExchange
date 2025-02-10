import {Component, OnInit} from '@angular/core';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FooterComponent} from "../../../components/footer/footer.component";
import {FormsModule} from "@angular/forms";
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {ItemForCart} from "../../../../services/models/item-for-cart";
import {UserControllerService} from "../../../../services/services/user-controller.service"
import {Cart} from "../../../../services/models/cart";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {BakeryNavBarResponse} from "../../../../services/models/bakery-nav-bar-response";

@Component({
  selector: 'app-riepilogo',
  imports: [
    FaIconComponent,
    FooterComponent,
    FormsModule,
    NavbarComponent,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './riepilogo.component.html',
  standalone: true,
  styleUrl: './riepilogo.component.scss'
})
export class RiepilogoComponent implements OnInit{


  constructor(
    private route: ActivatedRoute,
    private userService: UserControllerService,
    private frontEndService: FrontEndControllerService,
  ) {
  }

  userId: number | undefined;
  data:Cart = {}
  azz:BakeryNavBarResponse = {}
  isLogged: boolean = false;


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
          console.error("Errore nel fetch dati dati azz: "+err);
        }
      })
    }
  }


  removeToCart(item: ItemForCart){
    if(item != undefined && item.id != undefined){
      this.userService.removeToCart({
        idItem: item.id
      }).subscribe({
        next: res => {
          window.location.reload()
        },
        error:() => {
          console.error("errore nella rimozione dal cart")
        }
      })
    }

  }



  removeOneToQuantity(item: ItemForCart) {
    if(item.quantity != undefined && item.id != undefined) {

      if(item.quantity > 1 ){

        item.quantity--;
        console.error("valore: "+ item.quantity)

        // TODO potrei aggiornare il valore direttamente in cart

        this.userService.modQuantityItem({
          idItemCart: item.id,
          qnt: item.quantity
        }).subscribe({
          next: () =>  {
            window.location.reload()
          },
          error: (err) => {
            console.error("Errore nella diminuzione qnt"+ err)
          }
        })
      }
      else{
        this.userService.removeToCart({
          idItem: item.id
        }).subscribe({
          next:() => {
            window.location.reload()
          },
          error: (err) => {
            console.error("errore diminuzione qnt e cancellazione")
          }
        })
      }
    }
  }

  addOneToQuantity(item: ItemForCart){
    if(item.id != undefined && item.quantity != undefined) {

      if(item.quantity < 99 ){
        item.quantity++;
        console.error("valore: "+ item.quantity)

        // TODO potrei aggiornare il valore direttamente in cart

        this.userService.modQuantityItem({
          idItemCart: item.id,
          qnt: item.quantity
        }).subscribe({
          next: () =>  {
            window.location.reload()
          },
          error: (err) => {
            console.error("Errore nella diminuzione qnt"+ err)
          }
        })



      }
    }
  }




}
