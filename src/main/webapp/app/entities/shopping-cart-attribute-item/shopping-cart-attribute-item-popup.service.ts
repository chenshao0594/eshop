import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShoppingCartAttributeItem } from './shopping-cart-attribute-item.model';
import { ShoppingCartAttributeItemService } from './shopping-cart-attribute-item.service';
@Injectable()
export class ShoppingCartAttributeItemPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shoppingCartAttributeItemService: ShoppingCartAttributeItemService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.shoppingCartAttributeItemService.find(id).subscribe((shoppingCartAttributeItem) => {
                this.shoppingCartAttributeItemModalRef(component, shoppingCartAttributeItem);
            });
        } else {
            return this.shoppingCartAttributeItemModalRef(component, new ShoppingCartAttributeItem());
        }
    }

    shoppingCartAttributeItemModalRef(component: Component, shoppingCartAttributeItem: ShoppingCartAttributeItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shoppingCartAttributeItem = shoppingCartAttributeItem;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
