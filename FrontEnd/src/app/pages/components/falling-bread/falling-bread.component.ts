import {Component, OnInit} from '@angular/core';
import {NgForOf, NgStyle} from "@angular/common";

@Component({
  selector: 'app-falling-bread',
  imports: [
    NgStyle,
    NgForOf
  ],
  templateUrl: './falling-bread.component.html',
  standalone: true,
  styleUrl: './falling-bread.component.scss'
})
export class FallingBreadComponent implements OnInit {

  breads: { left: string, delay: string, duration: string, emoji: string }[] = [];
  breadEmoji: string[] = ['🍞', '🥖', '🥯', '🥐','🥨','🍕','🥧','🥪','🎂'];

  ngOnInit(): void {
    this.generateBreads();
  }

  generateBreads() {
    const breadCount = 100; // Numero di emoji di pane
    for (let i = 0; i < breadCount; i++) {
      const left = Math.random() * 100 + 'vw';
      const delay = Math.random() * 10 + 's';
      const duration = Math.random() * 5 + 5 + 's';
      const emoji = this.breadEmoji[Math.floor(Math.random() * this.breadEmoji.length)];
      this.breads.push({ left, delay, duration, emoji });
    }
  }


}
