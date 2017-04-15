import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ShoppingCartAttributeItem } from './shopping-cart-attribute-item.model';
import { ShoppingCartAttributeItemPopupService } from './shopping-cart-attribute-item-popup.service';
import { ShoppingCartAttributeItemService } from './shopping-cart-attribute-item.service';

@Component({
    selector: 'jhi-shopping-cart-attribute-item-delete-dialog',
    templateUrl: './shopping-cart-attribute-item-delete-dialog.component.html'
})
export class ShoppingCartAttributeItemDeleteDialogComponent {

    shoppingCartAttributeItem: ShoppingCartAttributeItem;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartAttributeItemService: ShoppingCartAttributeItemService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartAttributeItem']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shoppingCartAttributeItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shoppingCartAttributeItemListModification',
                content: 'Deleted an shoppingCartAttributeItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shopping-cart-attribute-item-delete-popup',
    template: ''
})
export class ShoppingCartAttributeItemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartAttributeItemPopupService: ShoppingCartAttributeItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.shoppingCartAttributeItemPopupService
                .open(ShoppingCartAttributeItemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
