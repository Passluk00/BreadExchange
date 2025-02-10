import {Component, OnInit} from '@angular/core';
import {UserControllerService} from "../../../services/services/user-controller.service";
import {Order} from "../../../services/models/order";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-order',
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './order.component.html',
  standalone: true,
  styleUrl: './order.component.scss'
})
export class OrderComponent implements OnInit{


  constructor(
    private userService: UserControllerService
  ) {
  }

  orders: Order[] = []


  ngOnInit() {
    this.getData()

  }


  getData(){
    this.userService.getAllOrders().subscribe({
      next: (res) => {
        this.orders= res
      },
      error: (err) => {
        console.error("Fetch Orders Fallito")
      }
    })
  }



}
