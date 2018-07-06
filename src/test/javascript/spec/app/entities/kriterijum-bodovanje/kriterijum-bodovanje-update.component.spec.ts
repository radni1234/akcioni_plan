/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumBodovanjeUpdateComponent } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje-update.component';
import { KriterijumBodovanjeService } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje.service';
import { KriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('KriterijumBodovanje Management Update Component', () => {
        let comp: KriterijumBodovanjeUpdateComponent;
        let fixture: ComponentFixture<KriterijumBodovanjeUpdateComponent>;
        let service: KriterijumBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumBodovanjeUpdateComponent]
            })
                .overrideTemplate(KriterijumBodovanjeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KriterijumBodovanjeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumBodovanjeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new KriterijumBodovanje(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kriterijumBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new KriterijumBodovanje();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kriterijumBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
