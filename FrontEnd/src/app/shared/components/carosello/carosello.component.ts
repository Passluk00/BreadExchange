import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-carosello',
  imports: [],
  templateUrl: './carosello.component.html',
  standalone: true,
  styleUrl: './carosello.component.scss'
})
export class CaroselloComponent {



  // TODO fare chiamata al back end che restituisca questi dati ( array di mappe )

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

}
