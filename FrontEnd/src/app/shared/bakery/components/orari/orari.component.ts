import {Component, Input, OnInit} from '@angular/core';
import {Week} from "../../../../services/models/week";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {getWeek} from "../../../../services/fn/front-end-controller/get-week";
import {NgIf} from "@angular/common";
import {LocalTime} from "../../../../services/models/local-time";

@Component({
  selector: 'app-orari',
  imports: [
    NgIf
  ],
  templateUrl: './orari.component.html',
  standalone: true,
  styleUrl: './orari.component.scss'
})
export class OrariComponent implements OnInit{

  constructor(
    private frontEndService: FrontEndControllerService
  ) {}

  @Input() id!: number | undefined;
  week: Week = {}

  ngOnInit(): void {
    this.getWeek()
  }


  getWeek(){
    if(this.id){
      this.frontEndService.getWeek({
        idBakery: this.id
      }).subscribe({
        next: (res) => {
          this.week = res;
          JSON.stringify(this.week, null)
        },
        error: (err) => {
          console.error("errore di fetch dati week: "+err);
        }
      })
    }
  }
}
