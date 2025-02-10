import {ChangeDetectorRef, Component, Inject, Input, OnInit, PLATFORM_ID} from '@angular/core';
import {AdminControllerService} from "../../../../services/services/admin-controller.service";
import {FrontEndControllerService} from "../../../../services/services/front-end-controller.service";
import {RandomDataBakeryResponse} from "../../../../services/models/random-data-bakery-response";
import {NgIf} from "@angular/common";
import {routes} from "../../../../app.routes";
import {Router} from "@angular/router";


@Component({
  selector: 'app-carosello-infinito',
  imports: [],
  templateUrl: './carosello-infinito.component.html',
  standalone: true,
  styleUrl: './carosello-infinito.component.scss'
})
export class CaroselloInfinitoComponent implements OnInit{


  constructor(
    private frontEndService: FrontEndControllerService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}


  dati:RandomDataBakeryResponse[] = []
  @Input() id!: number | undefined;


  ngOnInit() {
    this.getData()
    this.cdr.detectChanges()
  }


  goToMenu(){
    if(this.id != undefined ){
      this.router.navigate([`/bakery/menu/${this.id}`]);
    }
  }

  async getData(){
    if( this.id != undefined ) {
      this.frontEndService.getRandomData({
        idBac: this.id
      }).subscribe({
        next: (res) => {
          this.dati = res;
        },
        error: (err) => {
          console.error("Errore nel fetch dati dati: "+err);
        }
      })

    }

  }

}
