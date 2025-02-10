import {Component, OnInit} from '@angular/core';
import {FrontEndControllerService} from "../../../services/services/front-end-controller.service";
import {RandomDataBakeryResponse} from "../../../services/models/random-data-bakery-response";
import {RandomDataBakeryCarosello} from "../../../services/models/random-data-bakery-carosello";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-carosello',
  imports: [
    RouterLink
  ],
  templateUrl: './carosello.component.html',
  standalone: true,
  styleUrl: './carosello.component.scss'
})
export class CaroselloComponent implements OnInit{

  constructor(
    private frontEndService: FrontEndControllerService
  ) {
  }

  data:RandomDataBakeryCarosello[] = []

  ngOnInit() {
    this.getData()
  }


  // TODO fare chiamata al back end che restituisca questi dati ( array di mappe )

  /*
  data: any =  [{

      name:"Reda",
      url_image:"./public/testImg/ceste_pane.jpeg",
      url_page:"http://localhost:4200/user",
      desc:"Panificio Diretto da i Fratelli reda"
    },
    {
      name:"Poli",
      url_image:"./public/testImg/grissini.jpeg",
      url_page:"http://localhost:4200/user",
      desc:"Panificio Diretto da i Fratelli Poli"
    },
    {
      name:"Gino",
      url_image:"./public/testImg/bread.jpeg",
      url_page:"http://localhost:4200/user",
      desc:"Panificio Diretto da i Fratelli Ginostri"

    }]

   */


  getData(){
    this.frontEndService.getRandomForCarosello().subscribe({
      next: (res) => {
        this.data = res
      },
      error: (err) => {
        console.error("Errore nel fetch dati: " + err)
      }
    })
  }






}
