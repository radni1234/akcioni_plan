/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatBodovanjeDeleteDialogComponent } from 'app/entities/projekat-bodovanje/projekat-bodovanje-delete-dialog.component';
import { ProjekatBodovanjeService } from 'app/entities/projekat-bodovanje/projekat-bodovanje.service';

describe('Component Tests', () => {
    describe('ProjekatBodovanje Management Delete Component', () => {
        let comp: ProjekatBodovanjeDeleteDialogComponent;
        let fixture: ComponentFixture<ProjekatBodovanjeDeleteDialogComponent>;
        let service: ProjekatBodovanjeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatBodovanjeDeleteDialogComponent]
            })
                .overrideTemplate(ProjekatBodovanjeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjekatBodovanjeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjekatBodovanjeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
