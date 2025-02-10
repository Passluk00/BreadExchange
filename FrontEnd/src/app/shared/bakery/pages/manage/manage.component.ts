import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../components/footer/footer.component";
import {NgForOf, NgIf} from "@angular/common";
import {BakeryControllerService} from "../../../../services/services/bakery-controller.service";
import {ActivatedRoute} from "@angular/router";
import {OrderFrontEnd} from "../../../../services/models/order-front-end";
import {Order} from "../../../../services/models/order";

@Component({
  selector: 'app-manage',
  imports: [
    NavbarComponent,
    FooterComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './manage.component.html',
  standalone: true,
  styleUrl: './manage.component.scss'
})
export class ManageComponent implements OnInit{

  constructor(
    private bakeryService: BakeryControllerService,
    private route: ActivatedRoute
  ) {
  }

  userId: number | undefined
  orders: OrderFrontEnd[] = []


  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.userId = +params['id']
    })
    this.getData()
  }



  getData(){
    this.bakeryService.getAllOrdersBakery().subscribe({
      next:(res) => {
        this.orders = res
      },
      error: (err) => {
        console.error("errore nel fetch dati ordini: "+ err)
      }
    })
  }


  accept(order: OrderFrontEnd){
    if(order.id != undefined){
      this.bakeryService.acceptOrder({
        idOrder: order.id,
      }).subscribe({
        next:(res) => {
          console.error("accettato")
          window.location.reload()
        },
        error:(err) => {
          console.error("accettato fallito "+ err)
        }
      })
    }
  }

  reject(order: OrderFrontEnd){
    if(order.id != undefined){
      this.bakeryService.rejectOrder({
        idOrder: order.id,
      }).subscribe({
        next:() => {
          console.error("Rifiutato")
          window.location.reload()
        },
        error:(err) => {
          console.error("Rifiutato fallito"+ err)
        }
      })
    }
  }

  send(order: OrderFrontEnd){
    if(order.id != undefined){
      this.bakeryService.changeStatusOrder({
        idOrder: order.id,
      }).subscribe({
        next:() => {
          console.error("Cambiato")
          window.location.reload()
        },
        error:(err) => {
          console.error("Cambiato fallito"+ err)
        }
      })
    }
  }



}
