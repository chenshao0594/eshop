import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ShoppingCartAttributeItem } from './shopping-cart-attribute-item.model';
import { ShoppingCartAttributeItemPopupService } from './shopping-cart-attribute-item-popup.service';
import { ShoppingCartAttributeItemService } from './shopping-cart-attribute-item.service';
import { ShoppingCartItem, ShoppingCartItemService } from '../shopping-cart-item';

@Component({
    selector: 'jhi-shopping-cart-attribute-item-dialog',
    templateUrl: './shopping-cart-attribute-item-dialog.component.html'
})
export class ShoppingCartAttributeItemDialogComponent implements OnInit {

    shoppingCartAttributeItem: ShoppingCartAttributeItem;
    authorities: any[];
    isSaving: boolean;

    shoppingcartitems: ShoppingCartItem[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private shoppingCartAttributeItemService: ShoppingCartAttributeItemService,
        private shoppingCartItemService: ShoppingCartItemService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartAttributeItem']);
        this.shoppingCartAttributeItem = new ShoppingCartAttributeItem();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.shoppingCartItemService.query().subscribe(
            (res: Response) => { this.shoppingcartitems = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCartAttributeItem.id !== undefined) {
            this.shoppingCartAttributeItemService.update(this.shoppingCartAttributeItem)
                .subscribe((res: ShoppingCartAttributeItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shoppingCartAttributeItemService.create(this.shoppingCartAttributeItem)
                .subscribe((res: ShoppingCartAttributeItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShoppingCartAttributeItem) {
        this.eventManager.broadcast({ name: 'shoppingCartAttributeItemListModification', content: 'OK'});
        this.isSaving = false;
        this.shoppingCartAttributeItem = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackShoppingCartItemById(index: number, item: ShoppingCartItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shopping-cart-attribute-item-popup',
    template: ''
})
export class ShoppingCartAttributeItemPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shoppingCartAttributeItemPopupService: ShoppingCartAttributeItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shoppingCartAttributeItemPopupService
                    .open(ShoppingCartAttributeItemDialogComponent, params['id']);
            } else {
                this.modalRef = this.shoppingCartAttributeItemPopupService
                    .open(ShoppingCartAttributeItemDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
