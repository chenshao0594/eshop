import { Component, OnInit, Renderer, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';
declare const jQuery: any;
@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html',
    styleUrls: [
        'menu.scss'
    ]
})
export class MenuComponent implements OnInit {

  sidebarHeight = 0;
  sidebarMenu: any = 0;
  isCollapseMerchant: boolean;
  isCollapseCategory: boolean;
  isCollapseCustomer: boolean;
  isCollapsedOrder: boolean;
  isCollapsedSetting: boolean;

  constructor(private renderer: Renderer, private el: ElementRef) {
        this.isCollapseMerchant = true;
        this.isCollapseCategory = true;
        this.isCollapseCustomer = true;
        this.isCollapsedOrder = true;
        this.isCollapsedSetting = true;
  }

  ngAfterViewInit() {
    this.sidebarMenu = this.el.nativeElement.querySelector('#side-nav');
//    if (window.innerWidth > 768) {
//      setTimeout(() => {
//       // jQuery(this.sidebarMenu).find('.accordion-group.active .accordion-body').collapse('show');
//      });
//    }
  }

  setSidebarHeight(event) {
    if (window.innerWidth < 768) {
      let sidebarMarginTop = parseInt(
        window.getComputedStyle(this.sidebarMenu).marginTop, 10
      );
      let sidebarMarginBottom = parseInt(
        window.getComputedStyle(this.sidebarMenu).marginBottom, 10
      );
      this.sidebarHeight = this.sidebarMenu.offsetHeight + sidebarMarginTop + sidebarMarginBottom;
      let closestAccordionGroup = event.target.closest('.accordion-group');
      let submenuHeight = 0;
      let submenuItems = closestAccordionGroup.querySelectorAll('ul > li');
      submenuItems.forEach(() => {
        submenuHeight += 26;
      });
      let expandedMenu = closestAccordionGroup
        .querySelector('.accordion-body')
        .getAttribute('aria-expanded');
      if (expandedMenu === 'false') {
        this.sidebarHeight += submenuHeight;
      } else {
        this.sidebarHeight -= submenuHeight;
      }
    }
  }

  collapseSubMenu(event) {
    let currentMenu = event.target
      .closest('.accordion-group')
      .querySelector('.accordion-body');
    let collapsingMenu = this.sidebarMenu
      .querySelector('.accordion-group .accordion-body.collapse.show');
    jQuery(collapsingMenu).collapse('hide');
    if (collapsingMenu && currentMenu !== collapsingMenu && window.innerWidth < 768) {
      let submenuHeight = 0;
      let submenuItems = collapsingMenu.querySelectorAll('li');
      submenuItems.forEach(() => {
        submenuHeight += 26;
      });
      this.sidebarHeight -= submenuHeight;
    }
  }

  sidebarBehavior(event) {
    this.setSidebarHeight(event);
    this.collapseSubMenu(event);
    this.renderer.setElementStyle(document
      .querySelector('.content'), 'margin-top', this.sidebarHeight + 'px');
  }
  ngOnInit(): void {
    // TODO Auto-generated method stub
    return;
  }
}