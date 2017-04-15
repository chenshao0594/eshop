import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SystemNotification } from './system-notification.model';
import { SystemNotificationPopupService } from './system-notification-popup.service';
import { SystemNotificationService } from './system-notification.service';

@Component({
    selector: 'jhi-system-notification-delete-dialog',
    templateUrl: './system-notification-delete-dialog.component.html'
})
export class SystemNotificationDeleteDialogComponent {

    systemNotification: SystemNotification;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private systemNotificationService: SystemNotificationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['systemNotification']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemNotificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'systemNotificationListModification',
                content: 'Deleted an systemNotification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-notification-delete-popup',
    template: ''
})
export class SystemNotificationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private systemNotificationPopupService: SystemNotificationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.systemNotificationPopupService
                .open(SystemNotificationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
