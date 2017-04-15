import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ShoppingCartItem } from './shopping-cart-item.model';
import { ShoppingCartItemPopupService } from './shopping-cart-item-popup.service';
import { ShoppingCartItemService } from './shopping-cart-item.service';

@Component({
    selector: 'jhi-shopping-cart-item-delete-dialog',
    templateUrl: './shopping-cart-item-delete-dialog.component.html'
})
export class ShoppingCartItemDeleteDialogComponent {

    shoppingCartItem: ShoppingCartItem;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartItemService: ShoppingCartItemService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartItem']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shoppingCartItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shoppingCartItemListModification',
                content: 'Deleted an shoppingCartItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shopping-cart-item-delete-popup',
    template: ''
})
export class ShoppingCartItemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartItemPopupService: ShoppingCartItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.shoppingCartItemPopupService
                .open(ShoppingCartItemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
